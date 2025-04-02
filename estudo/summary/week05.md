# Location
- [code lab01](https://codelabs.developers.google.com/codelabs/while-in-use-location/#6)
- [code lab02](https://developers.google.com/codelabs/maps-platform/maps-platform-101-compose#0)
- [youtube01](https://youtu.be/Jj14sw4Yxk0?si=1h9KlNXk_WeV-uIw)
- [youtube02](https://www.youtube.com/watch?v=jMmQHNI8_H4)

## Maps frameworks
- `Google Maps SDK`
  - is easiest for many developers
  - Not open source and requires the device to have Google Play Services.
  - Requires a Google Account with billing enabled (but free up to usage limits)
- `Mapbox Maps SDK`
    - balanced approach of open-source approach and robust extras (can be considered “open core.”)
    - It is free to start, with advanced features requiring payment beyond free usage limits.
    - Has a big ecosystem of plugins and good tooling, but there are usage caps and proprietary extensions.

# Sensors
- `Motion Sensors` These sensors measure acceleration forces and rotational forces along three axes. This category includes accelerometers, gravity sensors, gyroscopes, and rotational vector sensors.
- `Environmental Sensors` These sensors measure various environmental parameters, such as ambient air temperature and pressure, illumination, and humidity.
- `Position Sensors` These sensors measure the physical position of a device. This category includes orientation sensors and magnetometers.

## SensorManager
You can use this class to create an instance of the sensor service. This class provides various methods for accessing and listing sensors, registering and unregistering sensor event listeners, and acquiring orientation information. This class also provides several sensor constants that are used to report sensor accuracy, set data acquisition rates, and calibrate sensors.

## Sensor
You can use this class to create an instance of a specific sensor. This class provides various methods that let you determine a sensor's capabilities.

## SensorEvent
The system uses this class to create a sensor event object, which provides information about a sensor event. A sensor event object includes the following information: the raw sensor data, the type of sensor that generated the event, the accuracy of the data, and the timestamp for the event.

## SensorEventListener
You can use this interface to create two callback methods that receive notifications (sensor events) when sensor values change or when sensor accuracy changes.

```kotlin
sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
    // Success! There's a magnetometer.
} else {
    // Failure! No magnetometer.
}
```
```kotlin
class SensorActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        val lux = event.values[0]
        // Do something with this sensor value.
    }

    override fun onResume() {
        super.onResume()
        mLight?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
```

# Services
## Foreground
- A foreground service performs some operation that is noticeable to the user. For example, an audio app would use a foreground service to play an audio track.

## Background
- A background service performs an operation that isn't directly noticed by the user. For example, if an app used a service to compact its storage, that would usually be a background service.

## Bound
- A service is bound when an application component binds to it by calling bindService(). A bound service offers a client-server interface that allows components to interact with the service, send requests, receive results, and even do so across processes with interprocess communication (IPC).

```kotlin
class HelloService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
}
```
- [other](https://developer.android.com/develop/background-work/services)

# Health
Android ’s Health Connect is a framework designed to centralize and standardize health and fitness data on mobile devices. It acts as a secure data repository that various apps can read from and write to, simplifying cross-app data sharing and reducing redundancy. Each participating app can read or write health metrics directly to this secure local repository once granted the user’s permission. By creating a single source of truth for health metrics (e.g., steps, calories, sleep), Health Connect enables developers to integrate with a unified API instead of implementing multiple proprietary integrations.
Health Services on Wear OS is the watch-specific framework that provides real-time access to sensor data and health tracking features (e.g., heart rate, steps) on Wear OS devices. It powers fitness apps on Wear OS watches by delivering optimized and battery-efficient health metrics.
When to Apply Each Strategy:
Use Health Connect when building a smartphone-based health or fitness app that requires aggregation of health data from multiple sources or when you want seamless cross-application sharing (e.g., a nutrition-tracking app leveraging step data from another app).
Use Health Services on Wear OS if you need real-time, watch-specific sensor data or are optimizing battery usage for a Wear OS fitness/health application. It’s best for apps heavily centered on wearable data (e.g., a heart rate monitor running during workouts).
In many cases, you might combine the two: gather real-time data on the watch through Health Services, then sync that data through Health Connect on the smartphone to integrate with other health and fitness apps.

![img_12.png](../img_12.png)
- Client app: To integrate with Health Connect, the client app links the SDK into their health and fitness app. This provides an API surface to interact with the Health Connect API.
- Software Development Kit: The SDK enables the client app to communicate with the Health Connect APK.
- Health Connect APK: This is the APK that implements Health Connect. It contains both its Permissions management and Data management components. The Health Connect APK is made available directly on the user's device, making Health Connect device-centric instead of account-centric.
- Permissions management: Health Connect includes a user interface through which apps request the user's permission to display data. It also provides a list of existing user permissions. This allows users to manage the access they have granted or denied to various applications.
- Data management: Health Connect provides a user interface with an overview of recorded data, whether it's a user's step counts, cycling speeds, heart rate, or other supported data types.

```kotlin
  /**
   * TODO: Writes an [ExerciseSessionRecord] to Health Connect.
   */
  suspend fun writeExerciseSession(start: ZonedDateTime, end: ZonedDateTime) {
    healthConnectClient.insertRecords(
      listOf(
        ExerciseSessionRecord(
          metadata = Metadata.manualEntry(),
          startTime = start.toInstant(),
          startZoneOffset = start.offset,
          endTime = end.toInstant(),
          endZoneOffset = end.offset,
          exerciseType = ExerciseSessionRecord.EXERCISE_TYPE_RUNNING,
          title = "My Run #${Random.nextInt(0, 60)}"
        ),
        StepsRecord(
          metadata = Metadata.manualEntry(),
          startTime = start.toInstant(),
          startZoneOffset = start.offset,
          endTime = end.toInstant(),
          endZoneOffset = end.offset,
          count = (1000 + 1000 * Random.nextInt(3)).toLong()
        ),
        TotalCaloriesBurnedRecord(
          metadata = Metadata.manualEntry(),
          startTime = start.toInstant(),
          startZoneOffset = start.offset,
          endTime = end.toInstant(),
          endZoneOffset = end.offset,
          energy = Energy.calories((140 + Random.nextInt(20)) * 0.01)
        )
      ) + buildHeartRateSeries(start, end)
    )
  }

  /**
   * TODO: Build [HeartRateRecord].
   */
  private fun buildHeartRateSeries(
    sessionStartTime: ZonedDateTime,
    sessionEndTime: ZonedDateTime,
  ): HeartRateRecord {
    val samples = mutableListOf<HeartRateRecord.Sample>()
    var time = sessionStartTime
    while (time.isBefore(sessionEndTime)) {
      samples.add(
        HeartRateRecord.Sample(
          time = time.toInstant(),
          beatsPerMinute = (80 + Random.nextInt(80)).toLong()
        )
      )
      time = time.plusSeconds(30)
    }
    return HeartRateRecord(
      metadata = Metadata.manualEntry(),
      startTime = sessionStartTime.toInstant(),
      startZoneOffset = sessionStartTime.offset,
      endTime = sessionEndTime.toInstant(),
      endZoneOffset = sessionEndTime.offset,
      samples = samples
    )
  }
/**
 * TODO: Reads in existing [WeightRecord]s.
 */
suspend fun readWeightInputs(start: Instant, end: Instant): List<WeightRecord> {
  val request = ReadRecordsRequest(
    recordType = WeightRecord::class,
    timeRangeFilter = TimeRangeFilter.between(start, end)
  )
  val response = healthConnectClient.readRecords(request)
  return response.records
}
```

- [other](https://developer.android.com/codelabs/health-connect#7)


