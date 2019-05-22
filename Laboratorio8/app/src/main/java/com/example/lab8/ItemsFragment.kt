package com.example.lab8

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8.databinding.FragmentItemsBinding

class ItemsFragment : Fragment() {
    companion object{
        var FECHA = ""
    }

    private lateinit var swipeBackground: ColorDrawable
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentItemsBinding = inflate(inflater, R.layout.fragment_items, container, false)

        swipeBackground = ColorDrawable(Color.parseColor("#ff0000"))

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = ItemAdapter()
        recyclerView.adapter = adapter

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.getAllItems().observe(this, Observer {
            adapter.setItems(it)
        })

        if(MainActivity.CODIGO != ""){
            for(a : Producto in ProductoFragment.productoViewModel.getAllProducts().value!!.listIterator()){
                val codigo = a.getCodigo()
                if(codigo == MainActivity.CODIGO){
                    itemViewModel.insert(Item(a.getNombre(), 0))
                    MainActivity.CODIGO = ""
                }
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                itemViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Item eliminado", Toast.LENGTH_LONG).show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                if(dX > 0){
                    swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                }

                swipeBackground.draw(c)
                c.save()

                if(dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)

                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.items_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemsFragment){
            itemViewModel.deleteAllItems()
            Toast.makeText(activity, "Lista reiniciada", Toast.LENGTH_LONG).show()
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        else if(item.itemId == R.id.inventariosFragment){
            FECHA = "27/05/2019"
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        return NavigationUI.onNavDestinationSelected(
            item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}
