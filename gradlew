#!/bin/sh
# Gradle wrapper - apunta al Gradle 8.9 ya cacheado localmente
GRADLE_HOME="$HOME/.gradle/wrapper/dists/gradle-8.9-bin/90cnw93cvbtalezasaz0blq0a/gradle-8.9"
exec "$GRADLE_HOME/bin/gradle" "$@"
