# Ok Noted - Android Note-Taking App (View vs. Compose Comparison)
![ComposeVsView](https://github.com/user-attachments/assets/0dc912b8-0ba3-4560-83b0-639b24206ff9)

**Compose (Left)** and **View (Right)**

---

**Ok Noted** is an Android note-taking app designed to demonstrate the differences between **traditional View-based UI** (XML) and modern **Jetpack Compose** implementations. This project serves as a practical comparison for developers to explore both paradigms side-by-side, with the same core features (create, read, edit, delete notes) built using different UI frameworks.

---

## 📖 Project Purpose  
This repository showcases:  
- **How the same app features** (note management) can be implemented in **View-based UI** vs. **Jetpack Compose**.  
- **Migration steps** from View to Compose (via the `compose` branch).  
- A clean, **Compose-only implementation** for reference.  

Developers can compare codebases to understand:  
✅ Structural differences between XML layouts and Compose components.  
✅ How state management and navigation differ between the two approaches.  
✅ The evolution of UI development in Android from Views to Compose.  

---

## 🌿 Branches Overview  

### 1. **`main` Branch**  
- **View-based UI** (traditional XML layouts).  
- Implements note management using `Activity`, and View components (e.g., `RecyclerView`).  
- **Manifest entry**: The `MainActivity` launches the View-based implementation.  

### 2. **`compose` Branch**  
- **Hybrid codebase** with both View and Compose components in the source code.  
- **Only the Compose UI is active** at runtime (the View components are retained for reference but not used).  
- **Purpose**: Demonstrates incremental migration to Compose while retaining legacy code for comparison.  
- **Manifest entry**: Launches a `MainComposeActivity` with Compose-based features.  

### 3. **`compose-only` Branch**  
- **Pure Jetpack Compose** implementation.  
- All View-based code (XML, Activities) has been removed.  
- Uses modern Compose patterns like `ViewModel`, `Navigation Compose`, and declarative UI.  

---

## 🚀 Getting Started  
1. **Clone the repository**:  
   ```bash
   git clone https://github.com/Dhanfinix/Ok_Noted.git
   ```

2. **Switch branches to compare implementations**:  
   - **View-based**:  
     ```bash
     git checkout main
     ```  
   - **Compose with legacy View code**:  
     ```bash
     git checkout compose
     ```  
   - **Compose-only**:  
     ```bash
     git checkout compose-only
     ```  

3. **Open in Android Studio** and run the app to explore the UI/UX differences.  

---

## 🔍 Code Comparison Tips  
Open The `compose` branch to compare easily see:  
  - How XML layouts translate to composable functions.  
  - Differences in navigation (`startActivity` vs. `NavHost`).  
  - Data handling (e.g., `RecyclerView.Adapter` vs. `LazyColumn`). 

---

**Happy coding!** Explore how Android UI development has evolved from View-based to Compose with Ok Noted 🚀  
