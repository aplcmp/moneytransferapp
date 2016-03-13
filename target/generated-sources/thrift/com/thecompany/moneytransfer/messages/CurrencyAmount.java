/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thecompany.moneytransfer.messages;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-03-13")
public class CurrencyAmount implements org.apache.thrift.TBase<CurrencyAmount, CurrencyAmount._Fields>, java.io.Serializable, Cloneable, Comparable<CurrencyAmount> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CurrencyAmount");

  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField PENNIES_FIELD_DESC = new org.apache.thrift.protocol.TField("pennies", org.apache.thrift.protocol.TType.I16, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CurrencyAmountStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CurrencyAmountTupleSchemeFactory());
  }

  public long value; // required
  public short pennies; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VALUE((short)1, "value"),
    PENNIES((short)2, "pennies");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // VALUE
          return VALUE;
        case 2: // PENNIES
          return PENNIES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VALUE_ISSET_ID = 0;
  private static final int __PENNIES_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PENNIES, new org.apache.thrift.meta_data.FieldMetaData("pennies", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CurrencyAmount.class, metaDataMap);
  }

  public CurrencyAmount() {
  }

  public CurrencyAmount(
    long value,
    short pennies)
  {
    this();
    this.value = value;
    setValueIsSet(true);
    this.pennies = pennies;
    setPenniesIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CurrencyAmount(CurrencyAmount other) {
    __isset_bitfield = other.__isset_bitfield;
    this.value = other.value;
    this.pennies = other.pennies;
  }

  public CurrencyAmount deepCopy() {
    return new CurrencyAmount(this);
  }

  @Override
  public void clear() {
    setValueIsSet(false);
    this.value = 0;
    setPenniesIsSet(false);
    this.pennies = 0;
  }

  public long getValue() {
    return this.value;
  }

  public CurrencyAmount setValue(long value) {
    this.value = value;
    setValueIsSet(true);
    return this;
  }

  public void unsetValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VALUE_ISSET_ID);
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return EncodingUtils.testBit(__isset_bitfield, __VALUE_ISSET_ID);
  }

  public void setValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VALUE_ISSET_ID, value);
  }

  public short getPennies() {
    return this.pennies;
  }

  public CurrencyAmount setPennies(short pennies) {
    this.pennies = pennies;
    setPenniesIsSet(true);
    return this;
  }

  public void unsetPennies() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PENNIES_ISSET_ID);
  }

  /** Returns true if field pennies is set (has been assigned a value) and false otherwise */
  public boolean isSetPennies() {
    return EncodingUtils.testBit(__isset_bitfield, __PENNIES_ISSET_ID);
  }

  public void setPenniesIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PENNIES_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((Long)value);
      }
      break;

    case PENNIES:
      if (value == null) {
        unsetPennies();
      } else {
        setPennies((Short)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VALUE:
      return getValue();

    case PENNIES:
      return getPennies();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VALUE:
      return isSetValue();
    case PENNIES:
      return isSetPennies();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CurrencyAmount)
      return this.equals((CurrencyAmount)that);
    return false;
  }

  public boolean equals(CurrencyAmount that) {
    if (that == null)
      return false;

    boolean this_present_value = true;
    boolean that_present_value = true;
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (this.value != that.value)
        return false;
    }

    boolean this_present_pennies = true;
    boolean that_present_pennies = true;
    if (this_present_pennies || that_present_pennies) {
      if (!(this_present_pennies && that_present_pennies))
        return false;
      if (this.pennies != that.pennies)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_value = true;
    list.add(present_value);
    if (present_value)
      list.add(value);

    boolean present_pennies = true;
    list.add(present_pennies);
    if (present_pennies)
      list.add(pennies);

    return list.hashCode();
  }

  @Override
  public int compareTo(CurrencyAmount other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetValue()).compareTo(other.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.value, other.value);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPennies()).compareTo(other.isSetPennies());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPennies()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pennies, other.pennies);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CurrencyAmount(");
    boolean first = true;

    sb.append("value:");
    sb.append(this.value);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pennies:");
    sb.append(this.pennies);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CurrencyAmountStandardSchemeFactory implements SchemeFactory {
    public CurrencyAmountStandardScheme getScheme() {
      return new CurrencyAmountStandardScheme();
    }
  }

  private static class CurrencyAmountStandardScheme extends StandardScheme<CurrencyAmount> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CurrencyAmount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.value = iprot.readI64();
              struct.setValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PENNIES
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.pennies = iprot.readI16();
              struct.setPenniesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CurrencyAmount struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(VALUE_FIELD_DESC);
      oprot.writeI64(struct.value);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PENNIES_FIELD_DESC);
      oprot.writeI16(struct.pennies);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CurrencyAmountTupleSchemeFactory implements SchemeFactory {
    public CurrencyAmountTupleScheme getScheme() {
      return new CurrencyAmountTupleScheme();
    }
  }

  private static class CurrencyAmountTupleScheme extends TupleScheme<CurrencyAmount> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CurrencyAmount struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetValue()) {
        optionals.set(0);
      }
      if (struct.isSetPennies()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetValue()) {
        oprot.writeI64(struct.value);
      }
      if (struct.isSetPennies()) {
        oprot.writeI16(struct.pennies);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CurrencyAmount struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.value = iprot.readI64();
        struct.setValueIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pennies = iprot.readI16();
        struct.setPenniesIsSet(true);
      }
    }
  }

}

