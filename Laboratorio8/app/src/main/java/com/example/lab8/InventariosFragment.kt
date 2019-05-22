package com.example.lab8


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8.databinding.FragmentInventariosBinding

class InventariosFragment : Fragment() {
    private lateinit var inventarioViewModel: InventarioViewModel
    private lateinit var swipeBackground: ColorDrawable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentInventariosBinding = inflate(inflater, R.layout.fragment_inventarios, container, false)

        swipeBackground = ColorDrawable(Color.parseColor("#ff0000"))

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = InventarioAdapter()
        recyclerView.adapter = adapter

        inventarioViewModel = ViewModelProviders.of(this).get(InventarioViewModel::class.java)
        inventarioViewModel.getAllInventarios().observe(this, Observer {
            adapter.setInventarios(it)
        })

        if(ItemsFragment.FECHA != ""){
            inventarioViewModel.insert(Inventario(ItemsFragment.FECHA))
            ItemsFragment.FECHA = ""
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                inventarioViewModel.delete(adapter.getProductAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "El producto ha sido eliminado", Toast.LENGTH_LONG).show()
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
        inflater.inflate(R.menu.inventarios_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemsFragment){
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        else if(item.itemId == R.id.inventariosFragment){
            inventarioViewModel.deleteAllInventarios()
            Toast.makeText(activity, "La Lista de inventarios se ha reiniciado", Toast.LENGTH_LONG).show()
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        return NavigationUI.onNavDestinationSelected(
            item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

}
