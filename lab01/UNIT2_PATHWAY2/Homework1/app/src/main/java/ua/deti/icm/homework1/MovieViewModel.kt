package ua.deti.icm.homework1

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {
    private val _tasks = getMovieTasks().toMutableStateList()
    val tasks: List<MovieTask>
        get() = _tasks

    fun remove(item: MovieTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: MovieTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }

    fun addTask(label: String, checked: Boolean) {
        _tasks.add(MovieTask(_tasks.size, label, checked))

    }
}



private fun getMovieTasks() = List(3) { i -> MovieTask(i, "Movie # $i") }
