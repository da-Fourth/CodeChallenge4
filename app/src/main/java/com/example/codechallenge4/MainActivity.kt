package com.example.codechallenge4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codechallenge4.ui.theme.CodeChallenge4Theme
import androidx.compose.runtime.*

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material.icons.Icons

import androidx.compose.material3.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowDropDown

//import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.clickable

import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeChallenge4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FormUI(modifier: Modifier = Modifier) {
    // State variables will be declared here (we'll go over them in a later step)
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    val genderOptions = listOf("Male", "Female", "Other")

    //Task 1: Add a Checkbox Section — “Select your hobbies”
    var hobbyMountaineering by remember { mutableStateOf(false) }
    var hobbyPhotography by remember { mutableStateOf(false) }
    var hobbyCoding by remember { mutableStateOf(false) }
    var hobbyEating by remember { mutableStateOf(false) }

    //Task 2: Add a Radio Button Group — “Select your experience level”
    val experienceOptions = listOf("Beginner", "Intermediate", "Advanced")
    var selectedExperienceLevel by remember { mutableStateOf("") }

    //Task 3: Add a Password Field
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Simple Form Example",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        //TextBox
        Text("Enter your name:")
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        //email input field
        Text("Enter your Email:")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //multiple line message
        Text("Enter your Message:")
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select your gender:")

        OutlinedTextField(
            value = if (selectedGender.isEmpty()) "Select Gender" else selectedGender,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = "Toggle Dropdown",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        selectedGender = gender
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        //Task 1: Add a Checkbox Section — “Select your hobbies”
        Text("Select your hobbies:")

        RowCheckbox("Mountaineering", hobbyMountaineering) { hobbyMountaineering = it }
        RowCheckbox("Photography", hobbyPhotography) { hobbyPhotography = it }
        RowCheckbox("Coding", hobbyCoding) { hobbyCoding = it }
        RowCheckbox("Eating", hobbyEating) { hobbyEating = it }

        Spacer(modifier = Modifier.height(16.dp))


        //Task 2: Add a Radio Button Group — “Select your experience level”
        Text("Select your experience level:")

        experienceOptions.forEach { level ->
            RowRadioButton(
                label = level,
                selected = selectedExperienceLevel == level,
                onSelect = { selectedExperienceLevel = level }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        //Task 3: Add a Password Field
        Text("Enter your password:")

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                val description = if (passwordVisible) "Hide password" else "Show password"

                Icon(
                    imageVector = icon,
                    contentDescription = description,
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

    }
}


//Task 1: Add a Checkbox Section — “Select your hobbies”
@Composable
fun RowCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        androidx.compose.material3.Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            modifier = Modifier
                .clickable { onCheckedChange(!checked) }
                .padding(start = 8.dp)
        )
    }
}


//Task 2: Add a Radio Button Group — “Select your experience level”
@Composable
fun RowRadioButton(label: String, selected: Boolean, onSelect: () -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        androidx.compose.material3.RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
