package com.tsdc.vinilos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tsdc.vinilos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)
        updateRoleChip()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_role -> { showRoleDialog(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRoleDialog() {
        val roles   = UserRole.values()
        val current = RoleManager.getRole(this)
        val names   = roles.map { it.displayName }.toTypedArray()
        val checked = roles.indexOf(current)
        AlertDialog.Builder(this)
            .setTitle("Seleccionar rol")
            .setSingleChoiceItems(names, checked) { dialog, which ->
                RoleManager.setRole(this, roles[which])
                updateRoleChip()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateRoleChip() {
        val role = RoleManager.getRole(this)
        binding.chipRole.text = role.displayName
        when (role) {
            UserRole.VISITANTE -> {
                binding.chipRole.setChipBackgroundColorResource(R.color.chip_visitante_bg)
                binding.chipRole.setTextColor(ContextCompat.getColor(this, R.color.chip_visitante_text))
            }
            UserRole.COLECCIONISTA -> {
                binding.chipRole.setChipBackgroundColorResource(R.color.chip_coleccionista_bg)
                binding.chipRole.setTextColor(ContextCompat.getColor(this, R.color.chip_coleccionista_text))
            }
        }
    }

    fun getCurrentRole(): UserRole = RoleManager.getRole(this)
}

interface RoleAware {
    fun onRoleChanged(role: UserRole)
}
