package ca.gbc.comp3074.porter_colin.lab4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomerAdapter(
    context: Context,
    private val customers: List<CustomerModel>
): ArrayAdapter<CustomerModel>(
    context,
    R.layout.customer_list_item,
    customers
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater
            .from(context).inflate(R.layout.customer_list_item, parent, false)
        val customer = customers[position]

        val tvID = view.findViewById<TextView>(R.id.tvID)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvAge = view.findViewById<TextView>(R.id.tvAge)

        tvID.text = "${customer.id}"
        tvName.text = customer.name
        tvAge.text = "${customer.age}"

        return view
    }
}