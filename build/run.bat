@echo off
set "PROJECT_NAME=DC2310 Word Net"
set "MAIN_ENTRY_POINT=uk.ac.aston.dc2300.ocean.world.Simulator"
set "TEST_ENTRY_POINT="
set "JUNIT=org.junit.runner.JUnitCore"
echo ======================== %PROJECT_NAME% ========================
::- JRE
set REQ_JAVA_VERSION=1.7
set JRE_REG="HKLM\SOFTWARE\JavaSoft\Java Runtime Environment"
set VERSION=CurrentVersion
set HOME=JavaHome
set JRE_VERSION=
set JREHOME=
set JAVA=

::- Project Root Directory
set PROJECT_ROOT=%~dp0\..

::- Locations relative to PROJECT_ROOT ------------------------------
::- Generated Classes Path
set SRC_TARGET=target\main\java
set TEST_TARGET=target\test\java
set SRC_TARGET_RES=target\main\resources
set TEST_TARGET_RES=target\test\resources

::- Dependencies
set DEP_JUNIT=lib\junit-4.12.jar
set DEP_HAMCREST=lib\hamcrest-core-1.3.jar
::-------------------------------------------------------------------

::- Get the JRE Version
reg query %JRE_REG% /v %VERSION% 1>nul || (
	echo JRE not installed!
	exit /b 1
)
for /f "tokens=2,*" %%a in ('reg query %JRE_REG% /v %VERSION% ^| findstr %VERSION%') do (
	set JRE_VERSION=%%b
)

::- Check JRE version is 1.7+
if %JRE_VERSION% LSS %REQ_JAVA_VERSION% (
	echo Build requires at least JavaSE1.7!
	exit /b 1
)

::- Get the JRE Home
reg query %JRE_REG%\%JRE_VERSION% /v %HOME% 1>nul || (
	echo JavaHome not installed!
	exit /b 1
)
for /f "tokens=2,*" %%a in ('reg query %JRE_REG%\%JRE_VERSION% /v %HOME% ^| findstr %HOME%') do (
	set JREHOME=%%b
)
set JAVA="%JREHOME%\bin\java.exe"

echo Starting %PROJECT_NAME%
cd /D %PROJECT_ROOT%

%JAVA% -cp %SRC_TARGET%;%SRC_TARGET_RES% %MAIN_ENTRY_POINT%
echo %PROJECT_NAME% finished

echo. & echo Running Tests
%JAVA% -cp %TEST_TARGET%;%TEST_TARGET_RES%;%DEP_JUNIT%;%DEP_HAMCREST% %JUNIT% %TEST_ENTRY_POINT%
echo %PROJECT_NAME% tests finished

echo. & echo Complete
echo ===================== ===================== =====================
@pause