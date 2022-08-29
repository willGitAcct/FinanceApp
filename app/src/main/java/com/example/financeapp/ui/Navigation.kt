package com.example.financeapp.ui


import androidx.annotation.DrawableRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.financeapp.HomePage
import com.example.financeapp.NotificationPage
import com.example.financeapp.StarPage

sealed class Screen(val title:String, val route: String, @DrawableRes val icons: Int){
    object Home:Screen(title = "home", "home_route", icons = com.example.financeapp.R.drawable.ic_baseline_home_24)
    object Notification:Screen(title = "notification", "notice_route", icons = com.example.financeapp.R.drawable.ic_baseline_mode_comment_24)
    object Star:Screen(title = "star", "star_route", icons = com.example.financeapp.R.drawable.ic_baseline_star_outline_24)


}

@Composable
fun BottomNavHost(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){
            HomePage()
        }
        composable(route = Screen.Notification.route){
            NotificationPage()
        }
        composable(route = Screen.Star.route){
            StarPage()
        }
    }
}

@Composable
fun BottomNavScreen(navController: NavController, item:List<Screen>){
    val navBackStackEntry by navController.currentBackStackEntryAsState()//whenever changed get the state insideteh backstack
    val currentDest= navBackStackEntry?.destination
    BottomNavigation() {
        item.forEach { screen ->
            BottomNavigationItem(selected = currentDest?.route == screen.route ,
                onClick = {
                    navController.navigate(screen.route){
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true //if any state here, it will be restored. if the above isnt saved, then this restore wont have an effect
                    }

                },
                icon ={ Icon(painter = painterResource(id = screen.icons), contentDescription = null)},
                label ={Text(screen.title)},
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.secondary)
        }
    }
}