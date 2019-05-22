package com.example.lab8

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.lab8.databinding.FragmentNuevoProductoBinding
import kotlinx.android.synthetic.main.fragment_nuevo_producto.*

class NuevoProductoFragment : Fragment() {
    companion object{
        var EXTRA_PRODUCT = ""
        var EXTRA_CODE = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentNuevoProductoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nuevo_producto, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun saveProducto(): Boolean{
        val nombre = editText2.text.toString()
        val codigo = editText1.text.toString()

        if(nombre.trim().isEmpty() || codigo.trim().isEmpty()) {
            Toast.makeText(context, "Ingrese un nombre y un codigo", Toast.LENGTH_LONG).show()
            return false
        }

        EXTRA_PRODUCT = nombre
        EXTRA_CODE = codigo
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(saveProducto())
            NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
        else{
            super.onOptionsItemSelected(item)
        }
    }
}
