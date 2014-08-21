package org.kesler.pkpvdimport.pvdimport.support;

import org.kesler.pkpvdimport.domain.*;
import org.kesler.pkpvdimport.domain.Package;
import org.kesler.pkpvdimport.pvdimport.DBReader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CauseReader extends DBReader {

    private Package aPackage;

    public CauseReader(Package aPackage) {
        this.aPackage = aPackage;
    }

    @Override
    public String getQuerySQL() {
        return "SELECT ID, ID_PACKAGE, STATUSMD, STARTDATE, ESTIMATEDATE, ID_PROC, STATE, STATUSMD, PURPOSE " +
                "from DPS$D_CAUSE " +
                "WHERE ID_PACKAGE='" + aPackage.getId() + "'";
    }

    @Override
    public void processRs(ResultSet rs) throws SQLException {
        if (rs==null) return;
        while (rs.next()) {
            Cause cause = new Cause();
            cause.setId(rs.getString("ID"));
            cause.setPackage(aPackage);
            cause.setProcId(rs.getString("ID_PROC"));
            cause.setStartDate(rs.getDate("STARTDATE"));
            cause.setEstimateDate(rs.getDate("ESTIMATEDATE"));
            cause.setState(rs.getInt("STATE"));
            cause.setStatusMD(rs.getString("STATUSMD"));
            cause.setPurpose(rs.getInt("PURPOSE"));

            aPackage.getCauses().add(cause);
        }

    }


}
