package com.example.forex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.example.forex.presentation.ui.TrialAndroidComposeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialAndroidComposeTheme {
                var count by remember {
                    mutableStateOf(0)
                }
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
                //ONCLICK LISTENER!
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    Text(
//                        text = count.toString(),
//                        fontSize = 30.sp
//                    )
//                    Button(onClick = { count++ }) {
//                        Text(text = "Click Me!")
//                    }
//                }
                var name by remember{
                    mutableStateOf("")
                }
                var names by remember{
                    mutableStateOf(listOf<String>() )
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    Row(Modifier.fillMaxWidth()) {
                        OutlinedTextField(value = name, onValueChange = {text -> name = text},
                            modifier = Modifier.weight(1f))
                        Spacer(modifier=Modifier.width(16.dp))
                        Button(onClick = { if (name.isNotBlank()) {
                            names = names + name
                            name = ""
                        } }) {
                            Text(text = "Add")
                        }
                    }
                    nameList(names)
                }

                //Greeting("Yuli")
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {


//    LazyRow {
//        items(10,  ) { i ->
//            Image(
//                painter= painterResource(id= R.drawable.ic_launcher_foreground),
//                contentDescription = null,
//                modifier = Modifier
//                    .background(Color.Black)
//                    .size(100.dp)
//            )
//        }
//    }
//
//    LazyColumn {
//        items(10,  ) { i ->
//            Image(
//                painter= painterResource(id= R.drawable.ic_launcher_foreground),
//                contentDescription = null,
//                modifier = Modifier
//                    .background(Color.Black)
//                    .size(100.dp)
//            )
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .size(500.dp),
//        contentAlignment = Alignment.BottomEnd,
//    ) {
//        Text(
//            text = "Hello $name!",
//            color = Color.Blue,
//            fontSize = 30.sp,
//            modifier = Modifier
//                .background(Color.Red)
//                .padding(20.dp)
//                .background(Color.Green)
//                .align(Alignment.BottomEnd)
//        )
//        Text(text = "Other text ", modifier = Modifier
//            .align(Alignment.BottomCenter))
//        Icon(imageVector = Icons.Default.Add, contentDescription = null,modifier = Modifier
//            .align(Alignment.BottomStart))
//        if (name.length > 5) {
//            Image(
//                painter= painterResource(id= R.drawable.ic_launcher_foreground),
//                contentDescription = null,
//                modifier = Modifier
//                    .background(Color.Black)
//                    .align(Alignment.CenterEnd)
//            )
//        }
//
//    }
//    Column(
//        horizontalAlignment = Alignment.End,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .size(500.dp)
//    ) {
//        Text(
//            text = "Hello $name!",
//            color = Color.Blue,
//            fontSize = 30.sp,
//            modifier = Modifier
//                .background(Color.Red)
//                .padding(20.dp)
//                .background(Color.Green)
//        )
//        Text(text = "Other text ")
//    }
//
//    Row(horizontalArrangement = Arrangement.End){
//        Text(
//            text = "Hello $name!",
//            color = Color.Blue,
//            fontSize = 30.sp,
//            modifier = Modifier
//                .background(Color.Blue)
//                .padding(20.dp)
//                .background(Color.Green)
//        )
//        Text(text = "Other text ")
//    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrialAndroidComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun nameList(
    names: List<String>
) {
    LazyColumn {
        items(names) {
                currentName ->
            Text(
                text = currentName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Divider()
        }
    }
}