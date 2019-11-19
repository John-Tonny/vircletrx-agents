package org.sysethereum.agents.constants;

import org.bitcoinj.core.Sha256Hash;
import org.libdohj.params.SyscoinTestNet3Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sysethereum.agents.core.bridge.SuperblockData;
import org.sysethereum.agents.core.syscoin.Keccak256Hash;
import org.sysethereum.agents.service.rest.MerkleRootComputer;

import java.math.BigInteger;
import java.util.List;

/**
 * AgentConstants for integration tests.
 * Uses Syscoin Mainnet and Eth Rinkeby.
 * Syscoin Mainnet is used for testing because Syscoin testnet is hard to use and mainnet syscoins are not that expensive.
 */
public class IntegrationAgentConstantsFactory {

    private static final Logger logger = LoggerFactory.getLogger("IntegrationAgentConstants");

    public IntegrationAgentConstantsFactory() {
    }

    public AgentConstants create() {
        var syscoinParams = SyscoinTestNet3Params.get();

        var syscoinToEthTimerTaskPeriod = 45 * 1000;

        List<Sha256Hash> sysHashes = List.of(Sha256Hash.wrap("00000f1a3b8f64527f2c0bd117b35ad27b845b650968fd3390e12ed231eec3b6"));

        var genesisSuperblock = new SuperblockData(
                MerkleRootComputer.computeMerkleRoot(syscoinParams, sysHashes),
                sysHashes,
                1574134667, 1574134435, 504365055,
                Keccak256Hash.wrap(new byte[32]), // initialised with 0s
                1
        );

        var defenderTimerTaskPeriod = 60 * 1000;
        var challengerTimerTaskPeriod = 60 * 1000;
        var defenderConfirmations = 2;
        var challengerConfirmations = 2;

        var networkId = "4"; // eth rinkeby 4; eth mainnet 1

        logger.info("genesisSuperblock " + genesisSuperblock.toString());

        return new AgentConstants(
                syscoinParams,
                syscoinToEthTimerTaskPeriod,
                genesisSuperblock,
                defenderTimerTaskPeriod,
                challengerTimerTaskPeriod,
                defenderConfirmations,
                challengerConfirmations,
                networkId
        );
    }
}
