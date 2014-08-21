package org.kesler.pkpvdimport.pvdimport.support;

import org.kesler.pkpvdimport.domain.Cause;
import org.kesler.pkpvdimport.domain.Obj;
import org.kesler.pkpvdimport.pvdimport.DBReader;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjReader extends DBReader{
    private Cause cause;

    public ObjReader(Cause cause) { this.cause = cause; }
    @Override
    public String getQuerySQL() {
        return "SELECT ID, ID_PACKAGE, ID_CAUSE, OBJTYPE, FULLADDRESS from DPS$OBJ " +
                "WHERE ID_CAUSE='" + cause.getId() + "'";
    }

    @Override
    public void processRs(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Obj obj = new Obj();
            obj.setId(rs.getString("ID"));
            obj.setCause(cause);
            obj.setPackage(cause.getPackage());
            obj.setObjtype(rs.getInt("OBJTYPE"));
            obj.setFullAddress(rs.getString("FULLADDRESS"));

            cause.getObjects().add(obj);
        }


    }

}
