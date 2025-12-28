#!/bin/sh
set -e

# Ensure gradle wrapper is executable
if [ -f "/workspace/gradlew" ]; then
    chmod +x /workspace/gradlew
fi

# Create gradle properties directory if not exists
mkdir -p ~/.gradle

# Configure Gradle daemon for better performance
cat > ~/.gradle/gradle.properties << 'EOF'
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.jvmargs=-Xmx2048m -XX:+HeapDumpOnOutOfMemoryError
EOF
# Download OpenJDK source code for better IDE support
if [ ! -f "/opt/java/openjdk/lib/src.zip" ]; then
    echo "Downloading OpenJDK source code..."
    cd /tmp
    curl -L -o temurin25.tar.gz "https://api.adoptium.net/v3/binary/latest/25/ga/linux/x64/jdk/hotspot/normal/eclipse?project=jdk" 2>/dev/null
    tar -xzf temurin25.tar.gz --wildcards "*/lib/src.zip"
    sudo cp jdk-*/lib/src.zip /opt/java/openjdk/lib/src.zip
    rm -rf temurin25.tar.gz jdk-*
    echo "Installed OpenJDK source code."
else
    echo "OpenJDK source code already installed."
fi
ls -la /opt/java/openjdk/lib/src.zip

echo "Development environment initialized successfully!"



# Execute the main command
exec "$@"

