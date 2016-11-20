package com.fivetran.truffle;

import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.AbstractTable;

class MockTable extends AbstractTable implements TranslatableTable {
    private final Class<?> type;
    private final Object[] rows;

    MockTable(Class<?> type, Object[] rows) {
        this.type = type;
        this.rows = rows;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        JavaTypeFactory javaTypes = (TruffleTypeFactory) typeFactory;

        return javaTypes.createStructType(type);
    }

    @Override
    public RelNode toRel(RelOptTable.ToRelContext context, RelOptTable table) {
        return new TMock(context.getCluster(), context.getCluster().traitSet().replace(TRel.CONVENTION), table, type, rows);
    }
}
