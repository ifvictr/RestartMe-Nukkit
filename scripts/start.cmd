@echo off
REM Set the TIMEOUT to how many seconds you want in between when the server stops to when the next restart takes place
set TIMEOUT=3
netstat -o -n -a | findstr 0.0.0.0:19132 > NUL
if %ERRORLEVEL% equ 0 (
    goto :loop
) else (
    echo "Script has been initialized."
    goto :start
)
:loop
ping 127.0.0.1 -n %TIMEOUT% > NUL
netstat -o -n -a | findstr 0.0:19132 > NUL
if %ERRORLEVEL% equ 0 (
    goto :loop
) else (
    ping 127.0.0.1 -n %TIMEOUT% > NUL
    echo "Server stopped. It'll be restarted in %TIMEOUT% second(s). You can press Ctrl+C to stop the restart process if you don't want to restart."
    goto :start
)
:start
if exist Nukkit.jar (
    java -jar Nukkit.jar
) else (
    echo "Couldn't find a Nukkit JAR installation..."
    pause
    exit 1
)
goto :loop