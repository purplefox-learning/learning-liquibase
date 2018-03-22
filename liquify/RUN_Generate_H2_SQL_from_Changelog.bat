@REM java -jar liquify.jar [-options] <source>
@REM Options:
@REM 
@REM Required:
@REM   -t,--type         Target changelog file type (xml,yaml,json,sql).
@REM 
@REM Conditional:
@REM   -db,--database    Database type to use when using the sql type (i.e oracle, h2, etc).
@REM       
@REM Example Usage:
@REM   java -jar liquify.jar -t sql -db oracle db.changelog.xml (db.changelog.xml => db.changelog.oracle.sql)


java -jar build/dist/libs/liquify.jar -t sql -db h2 test-db-changelog-master.xml