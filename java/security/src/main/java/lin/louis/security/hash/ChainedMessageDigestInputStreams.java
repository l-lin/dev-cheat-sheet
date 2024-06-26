package lin.louis.security.hash;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


class ChainedMessageDigestInputStreams extends FilterInputStream {

	private final Map<String, MessageDigestInputStream> messageDigestInputStreams = new LinkedHashMap<>();

	private final Map<String, byte[]> hashes = new HashMap<>();

	ChainedMessageDigestInputStreams(Map<String, MessageDigest> hashes, InputStream inputStream) {
		super(inputStream);
		chainMessageDigests(hashes);
	}

	Map<String, byte[]> computeHashes() {
		if (hashes.isEmpty()) {
			for (Entry<String, MessageDigestInputStream> mis : messageDigestInputStreams.entrySet()) {
				var messageDigest = mis.getValue().getMessageDigest();
				var algorithm = messageDigest.getAlgorithm();
				var provider = messageDigest.getProvider().getName();
				var hashDigest = mis.getValue().getDigest();
				hashes.put(provider + ":" + algorithm, hashDigest);
			}
		}
		return hashes;
	}

	private void chainMessageDigests(Map<String, MessageDigest> hashes) {
		for (Entry<String, MessageDigest> hash : hashes.entrySet()) {
			in = new MessageDigestInputStream(in, hash.getValue());
			messageDigestInputStreams.put(hash.getKey(), (MessageDigestInputStream) in);
		}
	}
}
