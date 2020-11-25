package com.codeturnal.kotlinblockchain

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeturnal.kotlinblockchainimport.BlockchainListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BlockchainListAdapter.Interaction {

    private val blockChain = BlockChain()
    lateinit var blockList : List<Block>
    lateinit var listAdapter: BlockchainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        blockList = blockChain.getBlocks()

        listAdapter.submitList(blockList)
        onClickButtonSend()
    }

    private fun onClickButtonSend(){
        buttonSend.setOnClickListener {
            if (editTextMainMessage.text!!.isNotEmpty()) {
                addNewBlock(editTextMainMessage.text.toString())
                dismissKeyboardAndSetEditTextToBlank()
            }
        }
    }

    private fun dismissKeyboardAndSetEditTextToBlank(){
        editTextMainMessage.setText("")
        try {
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            print(e)
        }
    }

    private fun addNewBlock(message: String) {
        if (blockChain.isValid() && blockList.isNotEmpty()) {
            val addedBlock = Block(blockChain.getLastBlock().hash, message)
            blockChain.add(addedBlock)
            blockList = blockChain.getBlocks()

            notifyAdapterAndScrollToAddedData()
        } else {
            val addedBlock = Block("0", message)
            blockChain.add(addedBlock)
            blockList = blockChain.getBlocks()
        }
    }

    private fun notifyAdapterAndScrollToAddedData(){
        listAdapter.notifyDataSetChanged()

        if (blockList.isNotEmpty())
        recyclerViewMain.smoothScrollToPosition(blockList.size - 1)
    }

    private fun setupRecyclerView(){
        listAdapter = BlockchainListAdapter(this)

        recyclerViewMain.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    override fun onItemSelected(position: Int, item: Block) {
        //nothing to do
    }


}