# Neo NEP UTXO Indexer

Sample project used to index notifications from a specific NEO Smart Contract. This sample projects uses notifications from [this](https://github.com/simplitech/neo-sc-bndes-example) sample Smart Contract.

## Important files

The most important file is the [IndexerProccess](https://github.com/simplitech/neo-nep-utxo-indexer/blob/master/src/main/java/org/neoneputxoindexer/admin/process/IndexerProcess.kt). Replace the apiPrefix variable using your own private network address.

## Logs must be enabled

In order for your node to store notifications, the logs plugin must be enabled. In versions prior to 2.9, you need to start the node using `--log` param. For older versions, use the ApplicationLogs plugin described [here](https://github.com/neo-project/neo-plugins)
