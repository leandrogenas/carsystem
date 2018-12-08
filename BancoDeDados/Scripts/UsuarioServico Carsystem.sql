CREATE LOGIN CarSystemSvcUsr WITH PASSWORD = '_svcusrCarSystem';
USE CarSystem;
CREATE USER CarSystemSvcUsr FOR LOGIN CarSystemSvcUsr;
EXEC sp_addrolemember N'db_datareader', N'CarSystemSvcUsr';
EXEC sp_addrolemember N'db_datawriter', N'CarSystemSvcUsr';
EXEC sp_addrolemember N'db_owner', N'CarSystemSvcUsr';
GO