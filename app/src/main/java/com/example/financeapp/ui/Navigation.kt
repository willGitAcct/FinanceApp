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
import com.example.financeapp.*

sealed class Screen(val title:String, val route: String, @DrawableRes val icons: Int){
    object Home:Screen(title = "Home", "home_page", icons = com.example.financeapp.R.drawable.ic_baseline_home_24)
    object Calculator:Screen(title = "Calculator", "calc_route", icons = com.example.financeapp.R.drawable.ic_baseline_calculate_24)
    object History:Screen(title = "Learning", "learning_route", icons = com.example.financeapp.R.drawable.ic_baseline_history_edu_24)

    object History2:Screen(title = "History", "history_route", icons = com.example.financeapp.R.drawable.ic_baseline_history_edu_24)
    object Basics:Screen(title = "Basics", "basics_route", icons = com.example.financeapp.R.drawable.ic_baseline_monetization_on_24)
    object Types:Screen(title = "Types", "types_route", icons = com.example.financeapp.R.drawable.ic_baseline_bar_chart_24)
    object Advanced:Screen(title = "Advanced", "adv_route", icons = com.example.financeapp.R.drawable.ic_baseline_functions_24)

}

@Composable
fun BottomNavHost(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){
            HomeScreen()
        }
        composable(route = Screen.Calculator.route){
            CalculatorPage()
        }
        composable(route = Screen.History.route){
            LearningPage()
        }
    }
}

//or the top nav in learning page
@Composable
fun TopNavHost(topNavHostController: NavHostController){
    NavHost(navController = topNavHostController, startDestination = Screen.History2.route){
        composable(route = Screen.History2.route){
            History()
        }
        composable(route = Screen.Basics.route){
            Basics()
        }
        composable(route = Screen.Types.route){
            Types()
        }
        composable(route = Screen.Advanced.route){
            Advanced()
        }
    }
}

@Composable
fun TopNavScreen(navController: NavController, item:List<Screen>){
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