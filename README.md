# connected-living-server

This is an early alpha version

Once loaded into eclipse, you will need to do a Maven update

This requires a mysql database server running, with a database CL.

Then, copy the src/main/resources/config/cl.properties to another location on your file system.  Edit this file, and provide the needed database parameters.

The user interface can be accessed by going to localhost:8080/user

Currently, there is no user management, and users need to be added manually to the database.  After starting the server, a table users should be created.
You will need to manually add a user with required permission.

Database entry has guid, username, password , and permissions.  The password is SHA-256 hash of the user password (salted).  The best way to get the required password, at this point, is to try logging into the user interface using the desired password.  Debug logging will log the hashed password.  Use this.

The permissions for a user is a JSONArray containing each robot, and the users permissions.  The robot name, the robot facility (set up on the Temi CL app), and the permissions .  Permission is a bit based permission.

```
[
  {
    "n": "Robot Name",
    "f": "Robot facility",
    "p": 11111
  }
]
```

Then, add the user like so (substitue USERNAME with your username, PASSWORD with the hash, and JSONARRAY with permission array)

```
mysql> INSERT INTO users (guid, username, password, permissions) VALUES ('1', 'USERNAME', 'PASSWORD', 'JSONARRAY');
```
