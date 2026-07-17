package turniplabs.halplibe.helper.metadata;

public class BlockMetadata {
	public static final int BLOCK_LENGTH = 8;
	public static final int NIBBLE_LENGTH = 4;
	private static final int MAX_METAVALUE = 0b1111_1111;

	/**
	 * In BTA block metadata can hold a byte worth of information.
	 * The index of each bit in the metadata is listed below.
	 * The original input is not altered.
	 * 7 6 5 4 3 2 1 0
	 * x x x x x x x x
	 */
	private BlockMetadata() {/* no need to initiate*/}

	/**
	 * Checks if a bit at index in the metadata is set.
	 * @param  metadata input to be manipulated
	 * @param index index to be checked
	 * @return true if the bit is set at the index, false otherwise
	 */
	public static boolean isSet(int metadata, int index) {
		return (BlockMetadata.getBit(metadata, index) & 1) == 1;
	}

	/**
	 * Returns the bit at index in the metadata.
	 * @param metadata input to be manipulated
	 * @param index index to return bit from
	 * @return bit set at index
	 */
	public static int getBit(int metadata, int index) {
		if (index >= BLOCK_LENGTH || index < 0) {
			return Metadata.NOT_METADATA;
		}
		return ((metadata & MAX_METAVALUE) >>> index) & 1;
	}

	/**
	 * Returns a sequence of bits in the metadata.
	 * @param metadata input to be manipulated
	 * @param startIndex start of the bit sequence
	 * @param endIndex end of the bit sequence
	 * @return sequence of bit in between start(inclusive) and end (inclusive) in the metadata
	 */
	public static int getBitBlock(int metadata, int startIndex, int endIndex) {
		if (startIndex >= BLOCK_LENGTH || startIndex < 0 || endIndex >= BLOCK_LENGTH || endIndex < 0) {
			return Metadata.NOT_METADATA;
		}
		int len = endIndex - startIndex;
		if (len < 0) {
			return Metadata.NOT_METADATA;
		}
		if (len == 0) {
			return Metadata.rawGetBit(metadata & MAX_METAVALUE, startIndex);
		}
		return Metadata.rawGetBitBlock(BLOCK_LENGTH, metadata & MAX_METAVALUE, startIndex, len + 1);
	}

	/**
	 * Returns the upper half of the byte.
	 * @param metadata input to be manipulated
	 * @return metadata upper half of the byte
	 */
	public static int getUpperBlock(int metadata) {
		return Metadata.rawGetUpperBlock(NIBBLE_LENGTH, metadata & MAX_METAVALUE);
	}


	/**
	 * Returns the lower half of the byte.
	 * @param metadata input to be manipulated
	 * @return lower half of the byte
	 */
	public static int getLowerBlock(int metadata) {
		return Metadata.rawGetLowerBlock(NIBBLE_LENGTH, metadata);
	}

	/**
	 * Sets a bit to 1 at index in the metadata
	 * @param metadata input to be manipulated
	 * @param index index to set a bit at
	 * @return metadata with 1 set at index
	 */
	public static int setBit(int metadata, int index) {
		return BlockMetadata.setBit(metadata, index, 1);
	}

	/**
	 * Sets a bit to 0 or 1 at index in the metadata
	 * @param metadata input to be manipulated
	 * @param index index to set a bit at
	 * @param value value to set the bit to
	 * @return metadata with value set at index
	 */
	public static int setBit(int metadata, int index, int value) {
		if (index >= BLOCK_LENGTH || index < 0) {
			return Metadata.NOT_METADATA;
		}
		return Metadata.rawSetBit(metadata & MAX_METAVALUE, index, value & 1);
	}

	/**
	 * Sets a sequence of bits in the metadata
	 * @param metadata input to be manipulated
	 * @param startIndex start of the bit sequence
	 * @param endIndex end of the bit sequence
	 * @param value value to set the bit sequence too
	 * @return metadata with a value set in the sequence from start (inclusive) to end (inclusive).
	 */
	public static int setBitBlock(int metadata, int startIndex, int endIndex, int value) {
		if (value > (MAX_METAVALUE >>> startIndex) || startIndex >= BLOCK_LENGTH || startIndex < 0 || startIndex > endIndex || endIndex > BLOCK_LENGTH) {
			return Metadata.NOT_METADATA;
		}
		return Metadata.rawSetBitBlock(metadata & MAX_METAVALUE, startIndex, endIndex - startIndex + 1, value);
	}

	/**
	 * Flips the bit at index in the metadata
	 * @param metadata input to be manipulated
	 * @param index index to flip a bit at
	 * @return metadata with the bit flipped at index
	 */
	public static int flipBit(int metadata, int index) {
		if (index >= BLOCK_LENGTH || index < 0) {
			return Metadata.NOT_METADATA;
		}
		return Metadata.rawFlipBit(metadata & MAX_METAVALUE, index);
	}

}
