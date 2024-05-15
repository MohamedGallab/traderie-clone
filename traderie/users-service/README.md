# user service

### Before running app:
1. go to git and run *docker-compose up*
2. go to localhost:5050 and run below sql queries
3. username: *traderie_user*
4. password: *passsword*
5. Then run app.
6. Do this only first time to run docker.

```sql
-- Create the "uuid-ossp" extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the "user" table
CREATE TABLE IF NOT EXISTS "user" (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    timezone VARCHAR(255),
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP,
    biography TEXT
);
```

##### If you want to bring container down run
```bash
docker-compose down
```
##### To bring it up again run this command before running app and make sure pgadmin as accessible (localhost:5050) so thant all data you have is not lost
```bash
docker-compose down
```
