CREATE LOGIN CarSystemSvcUsr WITH PASSWORD = '_svcusrCarSystem';
USE CarSystem;
CREATE USER CarSystemSvcUsr FOR LOGIN CarSystemSvcUsr;
EXEC sp_addrolemember N'db_datareader', N'yCarSystemSvcUsr';
EXEC sp_addrolemember N'db_datawriter', N'yCarSystemSvcUsr';
EXEC sp_addrolemember N'db_owner', N'yCarSystemSvcUsr';
GO