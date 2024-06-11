CREATE TABLE floor_plan_users (
   user_id INTEGER,
   floor_plan_id INTEGER,
   PRIMARY KEY (user_id, floor_plan_id),
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (floor_plan_id) REFERENCES floorplan(id) ON DELETE CASCADE
);