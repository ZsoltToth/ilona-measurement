create table if not exists Zone (
	zoneId varchar(40) primary key,
	name varchar(30)
);

create table if not exists Position (
	posId varchar(40) primary key,
	coord_X double,
	coord_Y double,
	coord_Z double,
	zoneId varchar(40),
	foreign key (zoneId) references Zone (zoneId)
	on delete cascade
);

create table if not exists Measurement (
	measId varchar(40) primary key,
	measTimestamp timestamp default current_timestamp,
	magnetometerX double,
	magnetometerY double,
	magnetometerZ double,
	magnetometerRadian double,
	gpsLatitude double,
	gpsLongitude double,
	gpsAltitude double,
	posId varchar(40),
	foreign key (posId) references Position (posId)
	on delete cascade
);

create table if not exists WIFIRSSI (
	ssid varchar(40),
	rssi  double,
	measId varchar(40),
	primary key(ssid, measId),
	foreign key (measId) references Measurement (measId)
	on delete cascade
);

create table if not exists RFIDTags (
	rfidTag binary(128),
	measId varchar(40),
	primary key(rfidTag,measId),
	foreign key (measId) references Measurement (measId)
	on delete cascade
);

create table if not exists BluetoothTags (
	btDeviceId varchar(40),
	measId varchar(40),
	primary key(btDeviceId, measId),
	foreign key(measId) references Measurement (measId)
	on delete cascade
);