package ua.deti.icm.homework1
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    MovieViewModel: MovieViewModel = viewModel()
) {
    Column(modifier = modifier) {
//        StatefulCounter()  // WATER COUNTER
        Text( text = "My WatchList", fontSize = 24.sp, modifier = Modifier.fillMaxWidth().padding(8.dp))
        MovieAdder(MovieViewModel = MovieViewModel)
        MovieTasksList(
            list = MovieViewModel.tasks,
            onCheckedTask = { task, checked ->
                MovieViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                MovieViewModel.remove(task)
            }
        )
    }
}
