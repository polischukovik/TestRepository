@echo off

SET SCRIPTS_DIR=scripts
SET CMS_NAME="vmws02:6400"
SET CMS_USER="Administrator"
SET CMS_PASS=""
SET UNX_PATH="/Interactive Insights/8.5.0/GI2_Universe.unx"

java -jar restrictions.jar %CMS_NAME% %CMS_USER% %CMS_PASS% %UNX_PATH% %SCRIPTS_DIR%