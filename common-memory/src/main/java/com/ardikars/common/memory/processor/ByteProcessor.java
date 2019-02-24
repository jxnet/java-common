package com.ardikars.common.memory.processor;

class ByteProcessor extends AbstractProcessor {

    private byte value;

    public ByteProcessor(byte value) {
        super(Byte.SIZE);
        this.value = value;
    }

    @Override
    public byte getBitByte(int index, int length) {
        return (byte) (value >> calculateSize(index, length));
    }

    @Override
    public short getBitUnsignedByte(int index, int length) {
        return reverseShort((short) ((value >> calculateSize(index, length)) & 0xff));
    }

    @Override
    public short getBitShort(int index, int length) {
        return reverseShort((short) (value >> calculateSize(index, length)));
    }

    @Override
    public short getBitShortLE(int index, int length) {
        return reverseShort((short) (value >> calculateSize(index, length)));
    }

    @Override
    public int getBitUnsignedShort(int index, int length) {
        return reverseInt(value >> calculateSize(index, length));
    }

    @Override
    public int getBitUnsignedShortLE(int index, int length) {
        return reverseInt(value >> calculateSize(index, length));
    }

    @Override
    public int getBitInt(int index, int length) {
        return reverseInt(value >> calculateSize(index, length));
    }

    @Override
    public int getBitIntLE(int index, int length) {
        return reverseInt(value >> calculateSize(index, length));
    }

    @Override
    public long getBitUnsignedInt(int index, int length) {
        return reverseLong((long) (value >> calculateSize(index, length)));
    }

    @Override
    public long getBitUnsignedIntLE(int index, int length) {
        return reverseLong((long) (value >> calculateSize(index, length)));
    }

    @Override
    public float getBitFloat(int index, int length) {
        return Float.intBitsToFloat(getBitInt(index, length));
    }

    @Override
    public float getBitFloatLE(int index, int length) {
        return Float.intBitsToFloat(getBitIntLE(index, length));
    }

    @Override
    public long getBitLong(int index, int length) {
        return reverseLong((long) (value >> calculateSize(index, length)));
    }

    @Override
    public long getBitLongLE(int index, int length) {
        return reverseLong((long) (value >> calculateSize(index, length)));
    }

    @Override
    public double getBitDouble(int index, int length) {
        return Double.longBitsToDouble(getBitLong(index, length));
    }

    @Override
    public double getBitDoubleLE(int index, int length) {
        return Double.longBitsToDouble(getBitIntLE(index, length));
    }

}
