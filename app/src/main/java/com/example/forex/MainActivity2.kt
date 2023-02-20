package com.example.forex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.*
import com.example.forex.presentation.market.MarketState
import com.example.forex.presentation.market.MarketViewModel
import com.example.forex.presentation.ui.TrialAndroidComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {
    private val viewModel: MarketViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialAndroidComposeTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    }, content = { paddingValue ->
                        Column {
                            Account(
                                equity = 1f,
                                balance = 2f,
                                margin = 3f,
                                used = 4f
                            )
                            Table()
                        }

                    }
                )
            }
        }
    }

    @Composable
    fun Loading() {
        val composition by rememberLottieComposition(LottieCompositionSpec.Url("https://assets8.lottiefiles.com/packages/lf20_rwq6ciql.json"))
        LottieAnimation(composition)
    }

    @Composable
    fun Table() {
        val marketUiState by viewModel.equityState.collectAsStateWithLifecycle()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Symbol",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Change",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sell",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Buy",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Divider(
            thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        )

        when (marketUiState) {
            is MarketState.Loading -> {
                AnimatedVisibility(
                    visible = marketUiState is MarketState.Loading,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> -fullHeight },
                    ) + fadeIn(),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> -fullHeight },
                    ) + fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                    {
                        Loading()
                    }
                }
            }
            is MarketState.Success -> {
                LazyColumn {
                    items(
                        (marketUiState as? MarketState.Success)?.market?.data?.listMarket
                            ?: listOf()
                    ) { market ->
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = market.symbol,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = market.change.toString(),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = market.sell.toString(),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = market.buy.toString(),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TrialAndroidComposeTheme {
            Account(1.0f, 2.0f, 3.0f, 4.0f)
        }
    }
}