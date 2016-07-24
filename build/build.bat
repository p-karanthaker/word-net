@echo off
set "PROJECT_NAME=DC2310 Word Net"
echo ===================== %PROJECT_NAME% Build =====================
::- JDK
set REQ_JAVA_VERSION=1.7
set JDK_REG="HKLM\SOFTWARE\JavaSoft\Java Development Kit"
set VERSION=CurrentVersion
set HOME=JavaHome
set JDK_VERSION=
set JDKHOME=
set JAVAC=

::- Project Root Directory
set PROJECT_ROOT=%~dp0\..

::- Locations relative to PROJECT_ROOT -------------------------------
::- Temporary Files Path
set SRC_TEXT=src_main.txt
set CLEANUP_TEXT=cleanup.txt

::- Source Code Path
set SRC=src\main\java
set SRC_RES=src\main\resources

::- Generated Classes Path
set SRC_TARGET=target\main\java
set SRC_TARGET_RES=target\main\resources
::--------------------------------------------------------------------

echo Checking Java installation...
::- Get the JDK Version
reg query %JDK_REG% /v %VERSION% 1>nul || (
	echo JDK not installed!
	exit /b 1
)
for /f "tokens=2,*" %%a in ('reg query %JDK_REG% /v %VERSION% ^| findstr %VERSION%') do (
	set JDK_VERSION=%%b
)

::- Check JDK version is 1.7+
if %JDK_VERSION% LSS %REQ_JAVA_VERSION% (
	echo Build requires at least JavaSE1.7!
	exit /b 1
)

::- Get the JDK Home
reg query %JDK_REG%\%JDK_VERSION% /v %HOME% 1>nul || (
	echo JavaHome not installed!
	exit /b 1
)
for /f "tokens=2,*" %%a in ('reg query %JDK_REG%\%JDK_VERSION% /v %HOME% ^| findstr %HOME%') do (
	set JDKHOME=%%b
)
set JAVAC="%JDKHOME%\bin\javac.exe"

::- Begin Build
echo Starting build...
cd /D %PROJECT_ROOT%

::- Clean up compiled classes
echo Cleaning up target directory...
dir /s/b %SRC_TARGET% > %CLEANUP_TEXT%
for /f "tokens=*" %%a in (%CLEANUP_TEXT%) do (
	rd /s/q "%%a" > nul 2>&1
)
:: Clean up resources from target
del /s/q %SRC_TARGET_RES% > nul 2>&1

::- Find Java source files and write to file.
dir /s/b %SRC%\*.java > %SRC_TEXT% 2>nul

::- Copy files from resource directories to target
echo Copying resources...
xcopy /s/q/y %SRC_RES% %SRC_TARGET_RES% 1>nul

::- Compile the source code files
echo Compiling java source files...
for /f "tokens=*" %%a in (%SRC_TEXT%) do (
	%JAVAC% -d %SRC_TARGET% -cp %SRC%;%SRC_RES% "%%a"
)

::- Clean up temporary text files
echo Post compile clean up...
del %SRC_TEXT% %CLEANUP_TEXT% 1>nul

echo Complete
echo ===================== ===================== =====================
@pause