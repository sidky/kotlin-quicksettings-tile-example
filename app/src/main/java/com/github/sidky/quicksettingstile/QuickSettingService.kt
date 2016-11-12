package com.github.sidky.quicksettingstile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

/**
 * This service handles all interaction in Quick Settings panel. It shows "Start Listening" and
 * "Stop listening" when the Quick Settings panel is shown/hidden. It shows the dialog, if clicked.
 * It asks you to unlock, if you click it from locked screen.
 */
class QuickSettingService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        Toast.makeText(applicationContext, "Start Listening", Toast.LENGTH_LONG).show()
    }

    override fun onStopListening() {
        super.onStopListening()
        Toast.makeText(applicationContext, "Stop Listening", Toast.LENGTH_LONG).show()
    }

    override fun onClick() {
        super.onClick()

        if (!isLocked) {
            toggleState(qsTile)
        } else {
            unlockAndRun { toggleState(qsTile) }
        }
    }

    private fun toggleState(tile: Tile) {
        val state = tile.state
        tile.state = if (state == Tile.STATE_ACTIVE) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
        Toast.makeText(applicationContext, "state: " + state, Toast.LENGTH_LONG).show()
        tile.icon = Icon.createWithResource(this, R.drawable.ic_adjust_white_24px)
        tile.updateTile()

        if (tile.state == Tile.STATE_ACTIVE) {
            showDialogWithMessage()
        }
    }

    private fun showDialogWithMessage() {
        val dialog = AlertDialog.Builder(applicationContext)
                .setTitle("Quick Tile")
                .setMessage("You clicked me!")
                .setPositiveButton("OK") { dialogInterface, i -> /* Do nothing */ }.create()

        showDialog(dialog)
    }
}
