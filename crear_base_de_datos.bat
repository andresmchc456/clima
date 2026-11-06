@echo off
set PGPASSWORD=admin123
"C:\Program Files\PostgreSQL\17\bin\createdb.exe" -U postgres -h 127.0.0.1 clima
echo.
echo =======================================================
echo  Base de datos 'clima' creada exitosamente!
echo =======================================================
echo.
pause

