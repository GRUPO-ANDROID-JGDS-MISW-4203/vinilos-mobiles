# Skill: ADB y Firebase Test Lab

## Objetivo

Usar ADB y Firebase Test Lab para validar apps Android fuera del flujo local basico.

## Cuando aplicar

- Validacion en varios dispositivos o versiones Android.
- Diagnostico de fallos que no aparecen en un solo emulador.
- Ejecucion cloud de pruebas instrumentadas.
- Smoke testing con Robo Test.

## ADB

Usar ADB para:

- detectar dispositivos,
- instalar APKs,
- abrir activities,
- simular interaccion,
- capturar logs,
- tomar screenshots y videos.

## Comandos base

```powershell
adb devices
adb install -r -g app-debug.apk
adb shell am start -n com.tsdc.vinilos/.MainActivity
adb logcat -c
adb shell screencap -p /sdcard/evidencia.png
adb pull /sdcard/evidencia.png .
```

## Firebase Test Lab

Usar FTL para:

- `Robo Test` cuando se necesita exploracion automatica de UI.
- `Instrumentation` cuando ya existen pruebas Espresso.
- matrices de dispositivos cuando hay riesgo de fragmentacion.

## Practica recomendada

- Probar localmente primero.
- Generar `app-debug.apk` y `app-debug-androidTest.apk`.
- Subir a FTL solo despues de validar lo basico.
- Si hay login o flujos bloqueantes, usar Robo Script.

## CLI base

```powershell
gcloud firebase test android run --type instrumentation --app app/build/outputs/apk/debug/app-debug.apk --test app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk --device model=redfin,version=30,locale=en,orientation=portrait --timeout 90s
```

## Riesgos a evitar

- Ejecutar cloud tests sin confirmar que los APKs compilan.
- Depender solo de Robo Test para validar logica de negocio.
- Ignorar costo y tiempo de matrices grandes.
- Romper localizadores de prueba sin revisar suites cloud.

## Checklist

- APK de app generado.
- APK de pruebas generado cuando aplica.
- Dispositivos objetivo definidos.
- Evidencia recuperable: logs, video, screenshots o reporte.
