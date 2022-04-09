//
//  CategoryGridView.swift
//  Coca
//
//  Created by Huynh Phong on 04/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CategoryGridView: View {
  var body: some View {
    ScrollView(.horizontal, showsIndicators: false) {
      LazyHGrid(rows: gridLayout, alignment: .center, spacing: 10, pinnedViews: []) {
        Section {
          ForEach(categories) { category in
            CategoryItemView(category: category)
          }
        } header: {
          SectionView(rotateClockwise: false)
        } footer: {
          SectionView(rotateClockwise: true)
        }
      } //: LazyHGrid
      .frame(height: 140)
        .padding(.horizontal, 16)
        .padding(.vertical, 10)
    } //: SrollView
  }
}

struct CatagoryGridView_Previews: PreviewProvider {
  static var previews: some View {
    CategoryGridView()
      .previewLayout(.sizeThatFits)
      .padding()
      .background(colorBackground)
  }
}

struct CategoryItemView: View {

  let category: Category

  var body: some View {
    Button {
      //
    } label: {
      HStack(alignment: .center, spacing: 6) {
        Image(category.image)
          .renderingMode(.template)
          .resizable()
          .scaledToFit()
          .frame(width: 30, height: 30, alignment: .center)
          .foregroundColor(.gray)

        Text(category.name.uppercased())
          .fontWeight(.light)
          .foregroundColor(.gray)

        Spacer()
      }
        .padding()
        .background(Color.white.cornerRadius(12))
        .background(RoundedRectangle(cornerRadius: 12).stroke(.gray, lineWidth: 1))
    }

  }
}

struct CategoryItemView_Previews: PreviewProvider {
  static var previews: some View {
    CategoryItemView(category: categories.first!)
      .previewLayout(.sizeThatFits)
      .padding()
      .background(colorBackground)
  }
}

struct SectionView: View {

  let rotateClockwise: Bool

  var body: some View {
    VStack {
      Spacer()

      Text("Categories".uppercased())
        .font(.footnote)
        .fontWeight(.bold)
        .foregroundColor(.white)
        .rotationEffect(.degrees(rotateClockwise ? 90 : -90))

      Spacer()
    } //: VStack
    .background(colorGray.cornerRadius(12))
      .frame(width: 85)
  }
}

struct SectionView_Previews: PreviewProvider {
  static var previews: some View {
    SectionView(rotateClockwise: true)
      .previewLayout(.fixed(width: 120, height: 240))
      .padding()
      .background(colorBackground)
  }
}
