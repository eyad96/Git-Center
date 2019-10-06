package com.yazan98.git.center

import com.yazan98.git.center.data.prefs.ApplicationPrefs
import com.yazan98.git.center.screen.MainScreen
import com.yazan98.git.center.screen.RegisterScreen
import io.atto.ui.base.AttoScreen
import io.atto.utils.utils.startScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AttoScreen() {
    override fun getLayoutRes(): Int = R.layout.screen_splash
    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            when (ApplicationPrefs.getUserStatus()) {
                true -> startScreen<MainScreen>(true)
                false -> startScreen<RegisterScreen>(true)
            }
        }
    }
}
