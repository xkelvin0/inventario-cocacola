@REM -------------------------------------------------------------------
@REM Maven Wrapper startup batch script
@REM -------------------------------------------------------------------
@IF "%MVNW_VERBOSE%"=="" @ECHO OFF
@setlocal

set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Find project basedir
@SET MAVEN_PROJECTBASEDIR=%~dp0
@IF NOT "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR%\

@REM Find java.exe
@SET JAVA_EXE=java.exe
@IF DEFINED JAVA_HOME SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
@IF NOT EXIST "%JAVA_EXE%" (
    @ECHO Error: JAVA_HOME is not defined correctly. >&2
    @ECHO Cannot find: %JAVA_EXE% >&2
    EXIT /B 1
)

@REM Maven Wrapper download
SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
SET WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

@IF NOT EXIST %WRAPPER_JAR% (
    @ECHO Downloading Maven Wrapper...
    powershell -Command "Invoke-WebRequest -Uri %WRAPPER_URL% -OutFile %WRAPPER_JAR% -UseBasicParsing"
    @IF ERRORLEVEL 1 (
        @ECHO Failed to download Maven Wrapper >&2
        EXIT /B 1
    )
)

@REM Run Maven via wrapper
"%JAVA_EXE%" ^
    -classpath %WRAPPER_JAR% ^
    -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" ^
    %WRAPPER_LAUNCHER% %*
