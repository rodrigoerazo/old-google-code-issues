package org.apache.ibatis.type;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseTypeHandlerTest {

  protected Mockery mockery = new Mockery() {
    {
      setImposteriser(ClassImposteriser.INSTANCE);
    }
  };

  protected final ResultSet rs = mockery.mock(ResultSet.class);
  protected final PreparedStatement ps = mockery.mock(PreparedStatement.class);
  protected final CallableStatement cs = mockery.mock(CallableStatement.class);

  public abstract void shouldSetParameter()
      throws Exception;

  public abstract void shouldGetResultFromResultSet()
      throws Exception;

  public abstract void shouldGetResultFromCallableStatement()
      throws Exception;

}
