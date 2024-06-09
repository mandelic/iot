drop table if exists user_group;

CREATE TABLE user_group
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_group_users
(
    user_id INTEGER,
    user_group_id INTEGER,
    PRIMARY KEY (user_group_id, user_id),
    FOREIGN KEY (user_group_id) REFERENCES user_group(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE floor_plan_groups (
   user_group_id INTEGER,
   floor_plan_id INTEGER,
   PRIMARY KEY (user_group_id, floor_plan_id),
   FOREIGN KEY (user_group_id) REFERENCES user_group(id) ON DELETE CASCADE,
   FOREIGN KEY (floor_plan_id) REFERENCES floorplan(id) ON DELETE CASCADE
);