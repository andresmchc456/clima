@echo off
set PGPASSWORD=admin123
echo ========================================================
echo   LIMPIANDO BASE DE DATOS Y REINICIANDO IDS A 1
echo ========================================================
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -h 127.0.0.1 -d clima -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"
echo.
echo Base de datos reseteada exitosamente.
echo Ahora los IDs volveran a empezar desde 1!
echo ========================================================
pause

