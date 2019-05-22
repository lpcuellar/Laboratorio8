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
import com.example.lab8.databinding.FragmentProductoBinding

class ProductoFragment : Fragment() {
    companion object{
        lateinit var productoViewModel: ProductoViewModel
    }

    private lateinit var swipeBackground: ColorDrawable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProductoBinding = inflate(inflater, R.layout.fragment_producto, container, false)

        swipeBackground = ColorDrawable(Color.parseColor("#ff0000"))


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = ProductAdapter()
        recyclerView.adapter = adapter

        productoViewModel = ViewModelProviders.of(this).get(ProductoViewModel::class.java)
        productoViewModel.getAllProducts().observe(this, Observer {
            adapter.setProducts(it)
        })

        if(NuevoProductoFragment.EXTRA_PRODUCT != "" && NuevoProductoFragment.EXTRA_CODE != ""){
            val nombre = NuevoProductoFragment.EXTRA_PRODUCT
            val codigo = NuevoProductoFragment.EXTRA_CODE
            val producto = Producto(nombre, codigo)
            productoViewModel.insert(producto)
            NuevoProductoFragment.EXTRA_PRODUCT = ""
            NuevoProductoFragment.EXTRA_CODE = ""
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                productoViewModel.delete(adapter.getProductAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Producto eliminado", Toast.LENGTH_LONG).show()
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
        inflater.inflate(R.menu.products_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.productoFragment){
            productoViewModel.deleteAllProducts()
            Toast.makeText(activity, "Todos los productos han sido eliminados del inventario", Toast.LENGTH_LONG).show()
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        else if(item.itemId == R.id.nuevoProductoFragment){
            return NavigationUI.onNavDestinationSelected(
                item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }
        return NavigationUI.onNavDestinationSelected(
            item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}
