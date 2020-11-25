package com.codeturnal.kotlinblockchain


class BlockChain {
    private var blocks: MutableList<Block> = mutableListOf()

    private val difficulty = 2
    private val validPrefix = "0".repeat(difficulty)

    fun add(block: Block) : Block {
        val minedBlock = if (isMined(block)) block else mine(block)
        blocks.add(minedBlock)
        return minedBlock
    }

    private fun isMined(block: Block) : Boolean {
        return block.hash.startsWith(validPrefix)
    }

    private fun mine(block: Block) : Block {

        println("Block unMined: $block")

        var minedBlock = block.copy()
        while (!isMined(minedBlock)) {
            minedBlock = minedBlock.copy(nonce = minedBlock.nonce + 1)
            println("Process Mining ${minedBlock.nonce}")
        }

        println("Block Mined : $minedBlock")

        return minedBlock
    }

    fun isValid() : Boolean {
        when {
            blocks.isEmpty() -> return true
            blocks.size == 1 -> return blocks[0].hash == blocks[0].calculateHash()
            else -> {
                for (i in 1 until blocks.size) {
                    val previousBlock = blocks[i - 1]
                    val currentBlock = blocks[i]

                    when {
                        currentBlock.hash != currentBlock.calculateHash() -> return false
                        currentBlock.previousHash != previousBlock.calculateHash() -> return false
                        !(isMined(previousBlock) && isMined(currentBlock)) -> return false
                    }
                }
                return true
            }
        }
    }

    fun getBlocks() : MutableList<Block> {
        return blocks
    }

    fun getLastBlock() : Block {
        return blocks.last()
    }
}