import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SimpleGUI {

	private long window; // window handle 
	
	public void run()
	{
		System.out.print( "Initialising Basic Pandemic GUI\n" );
		init();	
		
		loop();	//Start main loop
		
		System.out.print( "Closing Basic Pandemic GUI" );
		
	}
	
	public void init()
	{
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint( System.err ).set(); // This is required for proper errors from LWJGL code
		
		// Initialise OpenGL Framework "GLFW" -> Most GLFW functions do not work without this step
		if ( !glfwInit() )
			throw new IllegalStateException( "Unable to initialize GLFW" );
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default (Hints are various properties of windows created with GLFW)
		glfwWindowHint( GLFW_VISIBLE, GLFW_FALSE ); // the window will stay hidden after creation
		glfwWindowHint( GLFW_RESIZABLE, GLFW_TRUE ); // the window will be resizable
		
		// Create the window
		window = glfwCreateWindow( 1024, 768, "Pandemic", NULL, NULL );
		if ( window == NULL )
			throw new RuntimeException( "Failed to create the GLFW window" );
		
		// |the below try is just centering the window, it's scary lookin because we
		// |have to get a stack frame to use for dynamic memory which we use for glfwGetWindowSize
		
		// Get the thread stack and push a new frame to allow dynamic memory use 
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int* pWidth = allocated memory on the stack
			IntBuffer pHeight = stack.mallocInt(1); // int* pHeight = allocated memory on the stack

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize( window, pWidth, pHeight );

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode( glfwGetPrimaryMonitor() );

			// Center the window
			glfwSetWindowPos(
				window,
				( vidmode.width() - pWidth.get(0) ) / 2,
				( vidmode.height() - pHeight.get(0) ) / 2
			);
		} // the stack frame is popped automatically
		
		// Now we set up a key callback to close the window on pressing escape
		glfwSetKeyCallback( window, ( window, key, scancode, action, mods )-> 
		{
			if ( key == GLFW_KEY_ESCAPE		 //if key pressed is ESC 
				 && action == GLFW_RELEASE ) //and the key was released
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		} );
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}
	
	
	private void loop()
	{
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.75f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) 
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	
	public static void main( String[] args )
	{
		new SimpleGUI().run();
	}
}