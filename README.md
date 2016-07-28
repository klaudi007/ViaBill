# ViaBill
Company example


Simple example about Company CRUD operators

DB mysql

```

CREATE TABLE `COMPANY` (
       `ID` int(11) NOT NULL AUTO_INCREMENT,
       `MODIFICATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       `NAME` varchar(128) DEFAULT NULL,
       `ADDRESS` varchar(128) DEFAULT NULL,
       `COUNTRY` varchar(64) DEFAULT NULL,
       `CITY` varchar(64) DEFAULT NULL,
       `PHONE` varchar(32) DEFAULT NULL,
       `EMAIL` varchar(32) DEFAULT NULL,
       `MODIFIED_BY` varchar(32) DEFAULT NULL,
       PRIMARY KEY (`ID`)
     );

```
