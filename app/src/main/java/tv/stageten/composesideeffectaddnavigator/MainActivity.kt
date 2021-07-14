package tv.stageten.composesideeffectaddnavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.navigation.*
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val customTabsNavigator = MyCustomNavigator()
      SideEffect {
        navController.navigatorProvider += customTabsNavigator
      }
      NavHost(
        navController = navController,
        startDestination = "home",
      ) {
        customDestination("home")
      }
    }
  }
}

@Navigator.Name("MyCustomNavigator")
class MyCustomNavigator : Navigator<MyCustomNavigator.Destination>() {

  override fun createDestination(): Destination {
    return Destination(this)
  }

  class Destination(navigator: MyCustomNavigator) : NavDestination(navigator)
}

fun NavGraphBuilder.customDestination(
  route: String,
  arguments: List<NamedNavArgument> = emptyList(),
  deepLinks: List<NavDeepLink> = emptyList(),
) {
  addDestination(
    MyCustomNavigator.Destination(provider[MyCustomNavigator::class]).apply {
      this.route = route
      arguments.forEach { (argumentName, argument) ->
        addArgument(argumentName, argument)
      }
      deepLinks.forEach { deepLink ->
        addDeepLink(deepLink)
      }
    }
  )
}
