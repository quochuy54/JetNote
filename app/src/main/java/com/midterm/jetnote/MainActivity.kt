package com.midterm.jetnote

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midterm.jetnote.ui.theme.JetNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midterm.jetnote.ViewModel.NoteViewModel
import com.midterm.jetnote.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteTheme {
                val noteViewModel = viewModel<NoteViewModel>()
                val noteList = remember {
                    MutableStateFlow(noteViewModel.noteList)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .height(300.dp)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CreateTextFieldHeader()
                        Divider(modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp))
                        ListNotes()
                    }
                }
            }
        }
    }
}

@Composable
fun CreateTextFieldHeader(noteViewModel: NoteViewModel = viewModel()){
    var title by remember {
            mutableStateOf("")
        }

    var description by remember {
         mutableStateOf("")
         }
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(horizontal = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = title,
            onValueChange = {
                title = it
        }, colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
        ),
        label = { Text(text = "Title")},
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            )

        TextField(value = description,
            onValueChange = {
               description = it
        },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
            ),
            label = { Text(text = "Add a note")},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        
        Button(onClick = {
                         if(title.isNotEmpty() && description.isNotEmpty()){
                             noteViewModel.addNote(Note(title = title, description = description, entryDate = Date()))
                             title = ""
                             description = ""
                             Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                         }
                         },
            shape = RoundedCornerShape(corner = CornerSize(15.dp))
            ) {
            Text(text = "Save")
        }
        
    }

}

@Composable
fun ListNotes(noteViewModel: NoteViewModel = viewModel()){
    val data = noteViewModel.noteList.collectAsState()
    LazyColumn(){
        items(data.value){
            item -> Surface(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
                .fillMaxWidth(),
            color = Color(0xFFDFE6E8),
            elevation = 6.dp,
            ) {
             Column(modifier = Modifier
                 .clickable { }
                 .padding(horizontal = 14.dp, vertical = 6.dp),
                 horizontalAlignment = Alignment.Start
             ) {
                 Text(text = item.title,
                 style = MaterialTheme.typography.subtitle2)
                 Text(text = item.description,
                 style = MaterialTheme.typography.subtitle1)
                 Text(text = formatDate(item.entryDate),
                 style = MaterialTheme.typography.caption
                     )
             }
        }
        }
    }

}


fun formatDate(date: Date) : String{

    val pattern = "yyyy-MM-dd HH:mm:ss";
    val simpleDateFormat = SimpleDateFormat(pattern);
    val date = simpleDateFormat.format(date.time);
    return date
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNoteTheme {
        Greeting("Android")
    }
}