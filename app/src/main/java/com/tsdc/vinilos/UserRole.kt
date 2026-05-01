package com.tsdc.vinilos

import android.content.Context

// ── Roles disponibles ─────────────────────────────────
enum class UserRole(val displayName: String) {
    VISITANTE("Visitante"),
    COLECCIONISTA("Coleccionista")
}

// ── Singleton para persistir el rol seleccionado ──────
object RoleManager {
    private const val PREFS_NAME = "vinilos_prefs"
    private const val KEY_ROLE   = "user_role"

    fun getRole(context: Context): UserRole {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val name  = prefs.getString(KEY_ROLE, UserRole.VISITANTE.name)
        return try { UserRole.valueOf(name!!) } catch (e: Exception) { UserRole.VISITANTE }
    }

    fun setRole(context: Context, role: UserRole) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_ROLE, role.name).apply()
    }

    fun isColeccionista(context: Context) = getRole(context) == UserRole.COLECCIONISTA
}
