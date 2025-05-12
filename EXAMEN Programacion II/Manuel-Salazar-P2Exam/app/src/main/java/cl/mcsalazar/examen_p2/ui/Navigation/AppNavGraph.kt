package cl.mcsalazar.examen_p2.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.mcsalazar.examen_p2.ui.screens.CobrosFormUI
import cl.mcsalazar.examen_p2.ui.screens.CobrosListUI

@Composable
fun AppNavGraphCobros(
    navController: NavHostController = rememberNavController())
{
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ){
        composable("inicio"){
            CobrosListUI(navController = navController)
        }
        composable("formulario"){
            CobrosFormUI(
                onClick = {navController.popBackStack()}
            )
        }
    }
}