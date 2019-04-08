import Core.Query

import java.lang.reflect.Array;

def connString = "jdbc:oracle:thin:@ad.toidicode.com:1521/VTDB1.adpia.vn";
def username = "vinadream";
def password = "qpxmska1305vt";

query = new Query(connString, username, password);

assert query instanceof  Query;

assert query.select(['*']).from('users').run() instanceof  List
