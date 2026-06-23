package com.example.appblocker

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import java.time.LocalTime

class AppBlockerService : AccessibilityService() {

    private val blockedApps = setOf(
        "com.android.settings",
        "com.instagram.android"
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val openedApp = event.packageName?.toString() ?: return

            if (blockedApps.contains(openedApp)) {
                triggerHomeRedirect()
            }
        }
    }

    private fun triggerHomeRedirect() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onInterrupt() {}
}
