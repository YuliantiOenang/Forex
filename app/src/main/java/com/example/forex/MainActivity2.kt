package com.example.forex

import android.graphics.Paint.Align
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.example.forex.domain.repository.model.Instrument
import com.example.forex.domain.repository.model.Market
import com.example.forex.presentation.ui.TrialAndroidComposeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialAndroidComposeTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    }, content = { paddingValue ->
                        Column() {
                            Account(
                                equity = 1f,
                                balance = 2f,
                                margin = 3f,
                                used = 4f
                            )
                            Table(Market(com.example.forex.domain.repository.model.Account(1f, 2f,3f,4f), listOf(
                                Instrument("ABC", 1f, 2f, 3f, 4f)
                            )))
                        }

                    }
                )
            }
        }
    }
}

@Composable
fun Table(markets: Market) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Symbol",
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Change",
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Sell",
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Buy",
            modifier = Modifier.weight(1f)
        )
    }
    Divider(
        thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    )
    LazyColumn {
        items(markets.listMarket) { market ->
            Row {
                Text(market.symbol, Modifier.weight(1f))
                Text(market.change.toString(), Modifier.weight(1f))
                Text(market.sell.toString(), Modifier.weight(1f))
                Text(market.buy.toString(), Modifier.weight(1f))
            }
        }
    }

}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("App title") },
        actions = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconButton(
                    onClick = { /* TODO: Open search */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun Account(equity: Float, balance: Float, margin: Float, used: Float) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight(Alignment.Top, false)
                .background(Color.Green)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row {
                    Text(
                        text = "Equity",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = equity.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                Row {
                    Text(
                        text = "Balance",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = balance.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
            Divider(
                color = Color.Black, modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row {
                    Text(
                        text = "Margin",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = margin.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                Row {
                    Text(
                        text = "Used",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = used.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
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
        Account(1.0f, 2.0f, 3.0f, 4.0f)
    }
}

@Composable
fun nameList(
    names: List<String>
) {
    LazyColumn {
        items(names) { currentName ->
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